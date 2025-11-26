package org.acme.bitvavo.websocket.command;

// The Command Pattern interface
interface WebSocketCommand {
    /**
     * Converts the command object into the JSON string required by the Bitvavo API.
     * @return A JSON string representing the command.
     */
    String toJson();
}