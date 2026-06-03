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
import dev.rollczi.litecommands.range.Range;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import me.lokka30.phantomworlds.PhantomWorlds;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * AliasWorldParameter
 *
 * @author creatorfromhell
 * @since 2.0.5.0
 */
public class AliasWorldParameter implements ArgumentResolverBase<CommandSender, World> {

  @Override
  public ParseResult<World> parse(final Invocation<CommandSender> invocation, final Argument<World> context, final RawInput input) {

    final List<String> arguments = input.seeAll();
    for(int size = arguments.size(); size > 0; size--) {
      final String argument = String.join(" ", arguments.subList(0, size));
      final World world = PhantomWorlds.worldManager().findWorld(argument);

      if(world == null) {
        continue;
      }

      input.next(size);
      return ParseResult.success(world);
    }

    return ParseResult.failure("Invalid world name");
  }

  @Override
  public Range getRange(final Argument<World> argument) {

    return Range.moreThan(0);
  }

  @Override
  public SuggestionResult suggest(final Invocation<CommandSender> invocation, final Argument<World> argument, final SuggestionContext context) {

    final Set<String> worlds = new LinkedHashSet<>();

    PhantomWorlds.worldManager().aliases.forEach((alias, worldName)->{
      if(Bukkit.getWorld(worldName) != null) {
        worlds.add(alias);
      }
    });

    for(final World world : Bukkit.getWorlds()) {
      worlds.add(world.getName());
    }
    return SuggestionResult.of(worlds);
  }
}
