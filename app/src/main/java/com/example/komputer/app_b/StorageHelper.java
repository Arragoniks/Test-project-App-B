package com.example.komputer.app_b;

import android.os.Environment;

import java.io.File;

public class StorageHelper {
    /*String path = Environment.getExternalStorageDirectory()
            .getAbsolutePath();
if (!path.trim().isEmpty()
		&& Environment.getExternalStorageState().equals(
            Environment.MEDIA_MOUNTED)) {
        testAndAdd(path, MountDeviceType.EXTERNAL_SD_CARD);
    }

    // Получаем ремувабл
    String rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE");
if (rawSecondaryStoragesStr != null
            && !rawSecondaryStoragesStr.isEmpty()) {
        // All Secondary SD-CARDs splited into array
        final String[] rawSecondaryStorages = rawSecondaryStoragesStr
                .split(File.pathSeparator);
        for (String rawSecondaryStorage : rawSecondaryStorages) {
            testAndAdd(rawSecondaryStorage,
                    MountDeviceType.REMOVABLE_SD_CARD);
        }
    }*/
}
enum MountDeviceType {
    EXTERNAL_SD_CARD, REMOVABLE_SD_CARD
}
