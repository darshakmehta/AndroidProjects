package mad.com.listviewdemo;

public class Color {

    String colorName;
    String colorHex;

    public Color(String colorName, String colorHex) {
        this.colorName = colorName;
        this.colorHex = colorHex;
    }

    @Override
    public String toString() {
        return this.colorName + " " + this.colorHex; //toString is called when adpater assigns list to ListView
    }
}
