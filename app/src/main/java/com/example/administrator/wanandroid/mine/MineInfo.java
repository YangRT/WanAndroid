package com.example.administrator.wanandroid.mine;

public class MineInfo {

        private Data data;
        private int errorCode;
        private String errorMsg;
        public void setData(Data data) {
            this.data = data;
        }
        public Data getData() {
            return data;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }
        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
        public String getErrorMsg() {
            return errorMsg;
        }



    public static class Data {

        private int coinCount;
        private int rank;
        private int userId;
        private String username;
        private int level;

        public void setCoinCount(int coinCount) {
            this.coinCount = coinCount;
        }
        public int getCoinCount() {
            return coinCount;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
        public int getRank() {
            return rank;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
        public int getUserId() {
            return userId;
        }

        public void setUsername(String username) {
            this.username = username;
        }
        public String getUsername() {
            return username;
        }


        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
}
