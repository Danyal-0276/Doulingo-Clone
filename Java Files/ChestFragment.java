package com.example.projectmaddoulingoclone;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChestFragment extends Fragment {
    private EditText eTPrompt;
    private Button btnSubmit;
    private TextView tVResult;
    private final String API_KEY = "AIzaSyD_m6rbKpXg018RfdmKqlh-gBkTA2G-OVo";  // Replace with your actual API key
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chest, container, false);

        // Initialize UI elements
        eTPrompt = view.findViewById(R.id.inputMessage);
        btnSubmit = view.findViewById(R.id.sendButton);
        tVResult = view.findViewById(R.id.chatResponse);

        btnSubmit.setOnClickListener(v -> {
            String prompt = eTPrompt.getText().toString();
            if (!prompt.isEmpty()) {
                sendRequestToChatbot(prompt);
                eTPrompt.setText(""); // Clear input after sending
            }
        });

        return view;
    }

    private void sendRequestToChatbot(String prompt) {
        executorService.execute(() -> {
            try {
                // ✅ 1. Build the Correct JSON Request
                JSONObject jsonRequest = new JSONObject();
                JSONArray contentsArray = new JSONArray();
                JSONObject contentObj = new JSONObject();
                JSONArray partsArray = new JSONArray();
                JSONObject partObj = new JSONObject();

                partObj.put("text", prompt);  // User's input
                partsArray.put(partObj);  // Wrap in an array
                contentObj.put("parts", partsArray);  // Add to content
                contentsArray.put(contentObj);  // Add to request
                jsonRequest.put("contents", contentsArray);  // Final request body

                // ✅ 2. API URL (Ensure it's correct)
                String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // ✅ 3. Send the request
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(jsonRequest.toString());
                writer.flush();
                writer.close();

                // ✅ 4. Get the response
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String response = new java.util.Scanner(connection.getInputStream()).useDelimiter("\\A").next();
                    Log.d("Chatbot Response", response);

                    // ✅ 5. Parse response correctly
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("candidates")) {
                        JSONObject candidate = jsonResponse.getJSONArray("candidates").getJSONObject(0);
                        if (candidate.has("content")) {
                            JSONArray responseParts = candidate.getJSONObject("content").getJSONArray("parts");
                            String reply = responseParts.getJSONObject(0).getString("text");

                            // ✅ 6. Update UI on the main thread
                            requireActivity().runOnUiThread(() -> tVResult.setText(reply));
                        } else {
                            requireActivity().runOnUiThread(() -> tVResult.setText("Error: No content in response."));
                        }
                    } else {
                        requireActivity().runOnUiThread(() -> tVResult.setText("Error: No candidates in response."));
                    }
                } else {
                    requireActivity().runOnUiThread(() -> tVResult.setText("Error: Unable to get response. Response code: " + responseCode));
                }
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                requireActivity().runOnUiThread(() -> tVResult.setText("Error: " + e.getMessage()));
            }
        });
    }
}
