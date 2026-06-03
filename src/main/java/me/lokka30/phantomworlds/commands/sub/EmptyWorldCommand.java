package me.lokka30.phantomworlds.commands.sub;
/*
 * Phantom Worlds
 * Copyright (C) 2023 - 2024 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

/**
 * EmptyWorldCommand
 *
 * @author creatorfromhell
 * @since 2.1.0
 */
public class EmptyWorldCommand {

  private static final List<String> EMPTY_WORLD_SETTINGS = Arrays.asList(
          "type:FLAT",
          "generatorSettings:{\"biome\":\"minecraft:the_void\",\"layers\":[],\"features\":false,\"lakes\":false}",
          "generateStructures:false",
          "spawnMobs:false",
          "spawnAnimals:false",
          "keepSpawnInMemory:false"
  );

  public static void onCommand(final CommandSender sender, final String worldName) {

    CreateCommand.onCommand(sender, worldName, World.Environment.NORMAL, EMPTY_WORLD_SETTINGS);
  }
}
