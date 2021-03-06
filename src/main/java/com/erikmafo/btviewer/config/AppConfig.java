package com.erikmafo.btviewer.config;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class AppConfig {

    public static AppConfig load(ApplicationEnvironment environment) {
        return ConfigInjectionUtil.loadConfigProperties(getConfigName(environment), AppConfig.class);
    }

    private static String getConfigName(ApplicationEnvironment environment) {
        return environment.isProduction() ?
                "config.properties" :
                String.format("config.%s.properties", environment.getName());
    }

    private final boolean useBigtableEmulator;
    private final boolean useInMemoryDatabase;

    @Inject
    public AppConfig(@Named("USE_BIGTABLE_EMULATOR") boolean useBigtableEmulator,
                     @Named("USE_IN_MEMORY_DATABASE") boolean useInMemoryDatabase) {
        this.useBigtableEmulator = useBigtableEmulator;
        this.useInMemoryDatabase = useInMemoryDatabase;
    }

    public boolean useBigtableEmulator() {
        return useBigtableEmulator;
    }

    public boolean useInMemoryDatabase() {
        return useInMemoryDatabase;
    }
}
