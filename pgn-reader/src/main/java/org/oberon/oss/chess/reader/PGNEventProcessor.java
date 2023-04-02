/*
 * 	Copyright © 2000 Fabien H. Dumay
 *
 * 	Licensed under the Apache License, Version 2.0 (the "License");
 * 	you may not use this file except in compliance with the License.
 * 	You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * 	Unless required by applicable law or agreed to in writing, software
 * 	distributed under the License is distributed on an "AS IS" BASIS,
 * 	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 	See the License for the specific language governing permissions and
 * 	limitations under the License.
 */

package org.oberon.oss.chess.reader;

import org.jetbrains.annotations.NotNull;

/**
 * Describes the contract for clients that wants to process PGN Game date.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
public interface PGNEventProcessor {
    void startGame();

    void addTag(
            @NotNull String tagName,
            @NotNull String stringValue
    );

    void startMoveSequence(int plyNumber);

    void addMove(
            int plyNumber,
            String moveText
    );

    void addComment(
            int plyNumber,
            String comment
    );

    void terminateMoveSequence();

    void endGame();
}
