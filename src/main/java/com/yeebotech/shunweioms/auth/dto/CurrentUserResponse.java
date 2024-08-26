package com.yeebotech.shunweioms.auth.dto;

import lombok.Data;
import java.util.List;

@Data
public class CurrentUserResponse {
    private boolean success;
    private UserData data;

    @Data
    public static class UserData {
        private String name;
        private String avatar;
        private String userid;
        private String email;
        private String signature;
        private String title;
        private String group;
        private List<Tag> tags;
        private int notifyCount;
        private int unreadCount;
        private String country;
        private String access;
        private Geographic geographic;
        private String address;
        private String phone;

        @Data
        public static class Tag {
            private String key;
            private String label;

            public Tag(String number, String 专注设计) {
            }
        }

        @Data
        public static class Geographic {
            private Province province;
            private City city;

            @Data
            public static class Province {
                private String label;
                private String key;

                public Province(String 浙江省, String number) {
                }
            }

            @Data
            public static class City {
                private String label;
                private String key;

                public City(String 杭州市, String number) {
                }
            }
        }
    }
}
