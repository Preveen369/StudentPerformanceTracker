package studentperformancetracker;

import studentperformancetracker.utils.BannerPrinter;
import studentperformancetracker.menu.MenuController;

public class App {
    public static void main(String[] args) {
        BannerPrinter.printBanner();
        new MenuController().start();
    }
}
