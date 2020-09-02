var currentCard;	// Stores which card is currently open. None is at the beginning.

function cardOpen(card) {
	// Empty dishes div.
	$("#dishesdiv").html();
	
	// Close previously open cards.
	$(currentCard).animate({
		height : '100px'
	}, "fast");
	 
	// Open selected card.
	$(card).animate({
		height : '200px'
	}, "fast");
	
	currentCard = card;
}