package net.paradoxsubject.nostromoutils;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NOSTROMOUtils implements ModInitializer {
	public static final String MOD_ID = "nostromoutils";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Initialized with success: " + MOD_ID);
	}
}
