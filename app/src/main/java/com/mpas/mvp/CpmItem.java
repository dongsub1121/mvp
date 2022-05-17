package com.mpas.mvp;

public class CpmItem {

        int image;
        String title;

    private UserActionListener mUserActionListener;

    public interface UserActionListener {
        public void onImageClicked();

        public void onTextClicked();
    }


        int getImage() {
            return this.image;
        }
        String getTitle() {
            return this.title;
        }

    public CpmItem(int image, String title) {
            this.image = image;
            this.title = title;
        }

    public void setUserActionListener(UserActionListener l) {
        this.mUserActionListener = l;
    }

}
