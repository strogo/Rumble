/*
 * Copyright (C) 2014 Lucien Loiseau
 * This file is part of Rumble.
 * Rumble is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Rumble is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with Rumble.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.disrupted.rumble.network.protocols.command;

import org.disrupted.rumble.database.objects.Contact;

/**
 * @author Lucien Loiseau
 */
public class CommandSendLocalInformation extends Command {

    private Contact local;
    private int     flags;

    public CommandSendLocalInformation(Contact local, int flags){
        this.local = local;
        this.flags = flags;
    }

    public Contact getContact() {
        return local;
    }

    public int getFlags() {
        return flags;
    }

    @Override
    public CommandID getCommandID() {
        return CommandID.SEND_LOCAL_INFORMATION;
    }

}
