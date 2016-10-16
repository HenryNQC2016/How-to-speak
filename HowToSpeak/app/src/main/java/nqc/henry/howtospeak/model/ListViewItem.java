package nqc.henry.howtospeak.model;

/**
 * Created by Henry on 07-Sep-16.
 */
public class ListViewItem {
    private String text;
    private int iconRes;

    public ListViewItem(String text, int iconRes) {
        this.text = text;
        this.iconRes = iconRes;
    }

    public ListViewItem() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }
}
