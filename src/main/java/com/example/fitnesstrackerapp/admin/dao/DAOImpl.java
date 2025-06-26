package com.example.fitnesstrackerapp.admin.dao;
//FINAL
import com.example.fitnesstrackerapp.admin.dto.UserActivityDto;
import com.example.fitnesstrackerapp.admin.dto.UserDto;
import com.example.fitnesstrackerapp.admin.dto.UserVitalDto;
import com.example.fitnesstrackerapp.admin.model.User;
import com.example.fitnesstrackerapp.admin.model.UserActivity;
import com.example.fitnesstrackerapp.admin.model.UserVital;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DAOImpl implements DAOInterface {
    private static final String RESOURCES_FOLDER = "appdata/";
    private static final String USER_FILE = "UserDetails.txt";
    private static final String VITALS_FILE = "UserVitals.txt";
    private static final String ACTIVITY_FILE = "UserActivities.txt";
    private static final String USER_TYPE = "UserType";
    private static final String USERVITAL_TYPE = "UserVitalType";
    private static final String USERACTIVITY_TYPE = "UserActivityType";

    private List<User> userDetails = new ArrayList<>();
    private List<UserVital> userVitalDetails = new ArrayList<>();
    private List<UserActivity> userActivityDetails = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();


    public DAOImpl() {
        this.userDetails = readObjectListFromJsonFile(USER_TYPE);
        this.userVitalDetails = readObjectListFromJsonFile(USERVITAL_TYPE);
        this.userActivityDetails = readObjectListFromJsonFile(USERACTIVITY_TYPE);
    }

    private Path getResourcesPath(String filename) {
        String jarDir = new File(DAOImpl.class.getProtectionDomain().getCodeSource().getLocation().getPath())
                .getParent();
        return Paths.get(jarDir, RESOURCES_FOLDER, filename);
    }


    @Override
    public List addObject(Object object, String objectType) {
        if ("UserType".equals(objectType)) {
            User user = new User();
            List<User> users = new ArrayList<>();

            UserDto userDto = (UserDto) object;

            user.setName(userDto.getName());
            Long newuserID = 1L;
            if (userDetails != null) {
                newuserID = userDetails.isEmpty() ? 1 : userDetails.getLast().getUserId() + 1;
            }
            user.setUserId(newuserID);
            user.setPhone(userDto.getPhone());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            users.add(user);

            userDetails.addAll(users);

            saveObjectListToJsonFile(userDetails, USER_TYPE);

            return userDetails;
        }
        if ("UserVitalType".equals(objectType)) {
            UserVital userVital = new UserVital();
            List<UserVital> userVitals = new ArrayList<>();
            UserVitalDto userVitalDto = (UserVitalDto) object;

            User user = getObjbyEmail(userVitalDto.getEmail());
            if (user != null) {
                userVital.setUserId(user.getUserId());
                userVital.setAge(userVitalDto.getAge());
                userVital.setHeight(userVitalDto.getHeight());
                userVital.setCurrent_weight(userVitalDto.getCurrent_weight());
                userVital.setTarget_weight(userVitalDto.getTarget_weight());
                userVitals.add(userVital);

                userVitalDetails.addAll(userVitals);
                try {
                    Path filePath = getResourcesPath("UserVitals" + ".txt");
                    Files.writeString(filePath, userVitalDetails.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

            } else {
                return null;
            }

        }
        if ("UserActivityType".equals(objectType)) {
            UserActivity userActivity = new UserActivity();
            List<UserActivity> userActivities = new ArrayList<>();
            UserActivityDto userActivityDto = (UserActivityDto) object;

            User user = getObjbyEmail(userActivityDto.getEmail());
            if (user != null) {
                userActivity.setUserId(user.getUserId());
                userActivity.setDate(userActivityDto.getDate());
                userActivity.setWorkoutType(userActivityDto.getWorkoutType());
                userActivity.setNutrition(userActivityDto.getNutrition());
                userActivities.add(userActivity);

                userActivityDetails.addAll(userActivities);

                try {
                    Path filePath = getResourcesPath("UserActivities" + ".txt");
                    Files.writeString(filePath, userActivityDetails.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }

        }
        return List.of();
    }


    @Override
    public List updateObjectListToJsonFile(UserDto userDto, String objectType) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        if (USER_TYPE.equals(objectType)) {
            List<User> users = userDetails;
            try {
                for (int i = 0; i < users.size(); i++) {
                    User user = users.get(i);
                    if (user != null && user.getEmail().equals(userDto.getEmail())) {
                        if (userDto.getPassword() != null) {
                            user.setPassword(userDto.getPassword());
                        }
                        if (userDto.getName() != null) {
                            user.setName(userDto.getName());
                        }
                        user.setUserId(user.getUserId());
                        user.setPhone(user.getPhone());
                        user.setEmail(user.getEmail());
                    }
                }


                String json = objectMapper.writeValueAsString(users) == null ? "" : objectMapper.writeValueAsString(users);

                try (FileWriter writer = new FileWriter(String.valueOf(getResourcesPath(USER_FILE)))) {
                    writer.write(json);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return users;

        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public List getAll() {
        return userDetails;
    }

    @Override
    public List getAllUserActivity() {
        return userActivityDetails;
    }

    @Override
    public String add(List object) {
        return "";
    }

    @Override
    public Object getObj(Long id) {
        if (userDetails != null && !userDetails.isEmpty()) {
            for (User user : userDetails) {
                if (user.getUserId().equals(id)) {
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public UserVital getObjVital(Long id) {
        if (userVitalDetails != null && !userVitalDetails.isEmpty()) {
            for (UserVital userVital : userVitalDetails) {
                if (userVital.getUserId().equals(id)) {
                    return userVital;
                }
            }
        }
        return null;
    }

    @Override
    public UserActivity getObjActivity(Long id) {
        if (userActivityDetails != null && !userActivityDetails.isEmpty()) {
            for (UserActivity userActivity : userActivityDetails) {
                if (userActivity.getUserId().equals(id)) {
                    return userActivity;
                }
            }
        }
        return null;
    }

    @Override
    public User getObjbyEmail(String email) {
        if (userDetails != null && !userDetails.isEmpty()) {
            for (User user : userDetails) {
                if (user.getEmail().equals(email)) {
                    return user;
                }
            }
        }
        return null;
    }


    @Override
    public List saveObjectListToJsonFile(List objects, String objectType) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        if (USER_TYPE.equals(objectType)) {
            List<User> users = new ArrayList<>();
            try {
                for (Object obj : objects) {
                    if (obj instanceof User) {
                        users.add((User) obj);
                    } else {
                        throw new IllegalArgumentException("List contains non-User objects");
                    }
                }

                String json = objectMapper.writeValueAsString(users) == null ? "" : objectMapper.writeValueAsString(users);

                try (FileWriter writer = new FileWriter(String.valueOf(getResourcesPath(USER_FILE)))) {
                    writer.write(json);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return users;
        } else if (USERVITAL_TYPE.equals(objectType)) {
            List<UserVital> userVitals = new ArrayList<>();
            try {
                userVitals = Arrays.asList((UserVital[]) objects.toArray());

                String json = objectMapper.writeValueAsString(userVitals) == null ? "" : objectMapper.writeValueAsString(userVitals);

                try (FileWriter writer = new FileWriter(String.valueOf(getResourcesPath(VITALS_FILE)))) {
                    writer.write(json);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return userVitals;
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public List readObjectListFromJsonFile(String objectType) {
        if (USER_TYPE.equals(objectType)) {
            List<User> users = new ArrayList<>();
            try {
                String json = new String(Files.readAllBytes(Paths.get(String.valueOf(getResourcesPath(USER_FILE)))));

                users = objectMapper.readValue(json, new TypeReference<List<User>>() {
                });

            } catch (IOException e) {
                e.printStackTrace();

            }
            return users;
        } else if (USERVITAL_TYPE.equals(objectType)) {
            List<UserVital> userVitals = new ArrayList<>();
            try {
                String json = new String(Files.readAllBytes(Paths.get(String.valueOf(getResourcesPath(VITALS_FILE)))));

                userVitals = objectMapper.readValue(json, new TypeReference<List<UserVital>>() {
                });

            } catch (IOException e) {
                e.printStackTrace();

            }
            return userVitals;

        } else if (USERACTIVITY_TYPE.equals(objectType)) {
            List<UserActivity> userActivities = new ArrayList<>();
            try {
                String json = new String(Files.readAllBytes(Paths.get(String.valueOf(getResourcesPath(ACTIVITY_FILE)))));
                userActivities = objectMapper.readValue(json, new TypeReference<List<UserActivity>>() {

                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return userActivities;

        } else {
            return null;
        }

    }
}
