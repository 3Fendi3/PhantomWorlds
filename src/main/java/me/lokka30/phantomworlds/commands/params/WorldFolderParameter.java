package me.lokka30.phantomworlds.commands.params;
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

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolverBase;
import dev.rollczi.litecommands.input.raw.RawInput;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.join.JoinArgument;
import dev.rollczi.litecommands.range.Range;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import me.lokka30.phantomworlds.commands.utils.WorldFolder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * WorldFolderParameter
 *
 * @author creatorfromhell
 * @since 2.0.5.0
 */
public class WorldFolderParameter implements ArgumentResolverBase<CommandSender, WorldFolder> {

  @Override
  public ParseResult<WorldFolder> parse(final Invocation<CommandSender> invocation, final Argument<WorldFolder> context, final RawInput input) {

    if(!input.hasNext()) {
      return ParseResult.failure("Invalid world directory specified!");
    }

    final String argument = readArgument(context, input);

    final File directory = Bukkit.getWorldContainer();
    final File worldDir = new File(directory, argument);

    if(!worldDir.exists()) {
      return ParseResult.failure("Invalid world directory specified!");
    }
    return ParseResult.success(new WorldFolder(argument));
  }

  @Override
  public Range getRange(final Argument<WorldFolder> argument) {

    if(argument instanceof JoinArgument<?> joinArgument) {
      return Range.range(1, joinArgument.getLimit());
    }

    return Range.ONE;
  }

  private String readArgument(final Argument<WorldFolder> context, final RawInput input) {

    if(context instanceof JoinArgument<?> joinArgument) {
      final List<String> arguments = new ArrayList<>();
      int limit = joinArgument.getLimit();

      while(limit > 0 && input.hasNext()) {
        arguments.add(input.next());
        limit--;
      }

      return String.join(joinArgument.getSeparator(), arguments);
    }

    return input.next();
  }

  @Override
  public SuggestionResult suggest(final Invocation<CommandSender> invocation, final Argument<WorldFolder> argument, final SuggestionContext context) {

    final List<String> folders = new ArrayList<>();
    final File directory = Bukkit.getWorldContainer();

    if(directory.exists()) {
      final File[] files = directory.listFiles();
      if(files == null) {
        return SuggestionResult.of(folders);
      }

      for(final File file : files) {
        final File levelDat = new File(file, "level.dat");
        if(file.isDirectory() && levelDat.exists() && Bukkit.getWorld(file.getName()) == null) {
          folders.add(file.getName());
        }
      }
    }
    return SuggestionResult.of(folders);
  }
}
