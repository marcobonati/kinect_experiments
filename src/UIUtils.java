import controlP5.DropdownList;
import processing.core.PApplet;

public class UIUtils extends PApplet {


    public DropdownList customize(DropdownList ddl, String title, String[] items) {
        // a convenience function to customize a DropdownList
        //ddl.setBackgroundColor(color(190));
        ddl.setItemHeight(20);
        ddl.setBarHeight(20);
        ddl.getCaptionLabel().set(title);
        ddl.getCaptionLabel().getStyle().marginTop = 0;
        ddl.getCaptionLabel().getStyle().marginLeft = 1;
        ddl.getValueLabel().getStyle().marginTop = 0;
        for (int i=0;i<items.length;i++) {
            ddl.addItem(items[i], items[i]);
        }
        ddl.setColorBackground(color(60));
        ddl.setColorActive(color(255, 128));
        ddl.close();
        return ddl;
    }

}
