var AntGame = {
    board: null,
    ladder: null,
    refreshIntervalInMs: 100,

    init: function($board, $ladder) {
        AntGame.board = $board;
        AntGame.ladder = $ladder;
        $.getJSON("/game/status", function(data) {
            AntGame.initialiseBoard(data);
            AntGame.updateBoard();
        });
    },

    updateBoard: function() {
        $.getJSON("/game/status", function(data) {
            var offset = 0;
            for (var y = 0; y < data.board.height; y++) {
                for (var x = 0; x < data.board.width; x++) {
                    $cell = $('#board-cell-' + offset);
                    $cell.removeClass('board-cell-type-' + $cell.data('type'));

                    for (var i = 0; i < 6; i++)
                        $cell.removeClass('board-cell-ant-slot-' + i);

                    $cell.addClass('board-cell-type-' + data.board.data[offset]);
                    $cell.data('type', data.board.data[offset]);
                    offset++;
                }
            }

            for (var i in data.players) {
                var player = data.players[i];

                if ($('#ladder-entry-' + player.slot).length > 0)
                    $('#ladder-entry-' + player.slot)
                        .text(player.name + "(" + player.score + ")");
                else
                    $('<div>')
                        .attr('id', 'ladder-entry-' + player.slot)
                        .addClass('ladder-entry')
                        .addClass('ladder-entry-slot-' + player.slot)
                        .text(player.name + "(" + player.score + ")")
                        .appendTo(AntGame.ladder);


                for (var j in player.ants) {
                    var ant = player.ants[j];
                    var offset = ant.x + ant.y * data.board.width;
                    $('#board-cell-' + offset).addClass('board-cell-ant-slot-' + player.slot);
                }
            }

            setTimeout(function() {
                AntGame.updateBoard();
            }, AntGame.refreshIntervalInMs);
        })
    },

    initialiseBoard: function(data) {
        AntGame.board.empty();
        var offset = 0;
        for (var y = 0; y < data.board.height; y++) {
            for (var x = 0; x < data.board.width; x++) {
                $('<div>')
                    .attr('id', 'board-cell-' + offset)
                    .addClass('board-cell')
                    .addClass('cell-x-' + x)
                    .addClass('cell-y-' + y)
                    .data('offset', offset)
                    .data('x', x)
                    .data('y', x)
                    .data('type', data.board.data[offset])
                    .addClass('board-cell-type-' + data.board.data[offset])
                    .appendTo(AntGame.board);

                offset++;
            }
        }

        AntGame.ladder.empty();
        for (var i in data.players) {
            var player = data.players[i];

            $('<div>')
                .attr('id', 'ladder-entry-' + player.slot)
                .addClass('ladder-entry')
                .addClass('ladder-entry-slot-' + player.slot)
                .text(player.name + "(" + player.score + ")")
                .appendTo(AntGame.ladder);

            for (var j in player.ants) {
                var ant = player.ants[j];
                var offset = ant.x + ant.y * data.board.width;
                $('#board-cell-' + offset).addClass('board-cell-ant-slot-' + player.slot);
            }
        }
    }
}