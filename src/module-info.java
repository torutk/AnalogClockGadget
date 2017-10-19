/*
 *  Copyright © 2017 toru
 */
module AnalogClockGadget {
    requires javafx.controls;
    requires javafx.fxml;
    exports com.torutk.gadget.analogclock;
    opens com.torutk.gadget.analogclock to javafx.fxml;
}
