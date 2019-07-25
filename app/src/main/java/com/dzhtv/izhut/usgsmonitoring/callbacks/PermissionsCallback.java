package com.dzhtv.izhut.usgsmonitoring.callbacks;

import java.util.List;

public interface PermissionsCallback {
    void getNeededPermissions(List<String> permissions);
}
