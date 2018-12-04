/*
 *  Copyright © 2018 toru
 */

module com.torutk.gadget.analogclock {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.torutk.gadget.analogclock to javafx.graphics, javafx.fxml;
}
