package snackView;

/**
 * ProjectName：cmframeutils
 * PackageName：snackView
 * FileName：OnVisibilityChangeListener.java
 * Date：2015/9/1 52
 * Author：大鹏
 * ClassName:OnVisibilityChangeListener
 **/
public interface OnVisibilityChangeListener {
    /**
     * Gets called when a message is shown
     *
     * @param stackSize the number of messages left to show
     */
    void onShow(int stackSize);

    /**
     * Gets called when a message is hidden
     *
     * @param stackSize the number of messages left to show
     */
    void onHide(int stackSize);
}
