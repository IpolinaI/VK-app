package ru.testtask.vkapp.business;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.testtask.vkapp.dto.Friend;
import ru.testtask.vkapp.dto.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService {
    private static String accessUrl;
    private static String apiUrl;

    public User getUserInfo(String code) {
        JSONObject accessData = getData(String.format(accessUrl, code));
        User user = null;

        if (accessData != null) {
            String token = null;
            Long userId = 0L;

            try {
                token = accessData.getString("access_token");
                userId = accessData.getLong("user_id");
            } catch(Exception exp) {
                exp.printStackTrace();
            }

            JSONObject userData = getData(String.format(apiUrl, "users.get", userId, "photo_200", token));
            JSONObject userFriendsData = getData(String.format(apiUrl, "friends.get", userId, "name", token));

            if (userData != null && userFriendsData != null) {
                try {
                    String photoUrl = userData.getJSONArray("response").getJSONObject(0).getString("photo_200");
                    JSONArray jsonFriends = userFriendsData.getJSONObject("response").getJSONArray("items");

                    List<Friend> friendsList = new ArrayList<Friend>();
                    for (int i = 0; i < jsonFriends.length(); i++) {
                        JSONObject friend = jsonFriends.getJSONObject(i);
                        friendsList.add(buildFriend(friend.getString("first_name"), friend.getString("last_name")));
                    }

                    user = buildUser(photoUrl, friendsList);
                } catch(Exception exp) {
                    exp.printStackTrace();
                }
            }
        }

        return user;
    }

    @Value("${access.token.url}")
    private void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    @Value("${api.url}")
    private void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    private User buildUser(String photoUrl, List<Friend> friends) {
        return User.builder()
                .friends(friends)
                .photoUrl(photoUrl)
                .build();
    }

    private Friend buildFriend(String firstName, String lastName) {
        return Friend.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }

    private JSONObject getData(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-length", "0");
            connection.setConnectTimeout(30000);

            connection.connect();

            int response = connection.getResponseCode();
            if (response == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder sb = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                reader.close();

                return new JSONObject(sb.toString());
            } else {
                log.error("Server Response: {}", response);
            }
        } catch(Exception exp) {
            exp.printStackTrace();
        }

        return null;
    }
}
