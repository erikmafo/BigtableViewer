package com.erikmafo.btviewer.services.internal;

import com.erikmafo.btviewer.model.BigtableInstance;
import com.google.cloud.bigtable.admin.v2.BigtableTableAdminSettings;
import com.google.cloud.bigtable.data.v2.BigtableDataSettings;

import java.io.IOException;

public interface BigtableSettingsProvider {

    BigtableTableAdminSettings getTableAdminSettings(BigtableInstance instance) throws IOException;

    BigtableDataSettings getDataSettings(BigtableInstance instance);
}
