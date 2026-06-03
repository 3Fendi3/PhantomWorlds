package me.lokka30.phantomworlds.managers;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class WorldDataCleaner {

  public Set<String> findMissingWorlds(final Collection<String> worldNames, final File worldContainer) {

    final Set<String> missingWorlds = new LinkedHashSet<>();
    if(worldContainer == null || !worldContainer.isDirectory()) {
      return missingWorlds;
    }

    for(final String worldName : worldNames) {
      if(!worldExists(worldContainer, worldName)) {
        missingWorlds.add(worldName);
      }
    }

    return missingWorlds;
  }

  public boolean worldExists(final File worldContainer, final String worldName) {

    final File worldFolder = new File(worldContainer, worldName);
    final File levelDat = new File(worldFolder, "level.dat");
    return worldFolder.isDirectory() && levelDat.isFile();
  }
}
