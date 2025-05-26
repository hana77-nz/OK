package models;

public class Review {
      //  private int id;
        private String text;
        private int rate; // 1-5
        private User user;


        public Review( String text, int rate, User user) {
           // this.id = id;
            this.text = text;
            this.rate = rate;
            this.user = user;
        }

        public String getText() { return text; }
        public int getRate() { return rate; }
        public User getUser() { return user; }


    }

