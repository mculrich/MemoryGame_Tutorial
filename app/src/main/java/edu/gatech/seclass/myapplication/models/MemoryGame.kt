package edu.gatech.seclass.myapplication.models

import android.util.Log
import androidx.core.os.persistableBundleOf
import edu.gatech.seclass.myapplication.utils.DEFAULT_ICONS

class MemoryGame(private val boardSize: BoardSize){


    val cards: List<MemoryCard>
    var numPairsFound = 0

    private var indexOfSingleSelectedCard: Int? = null

    init {
        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randomizedImages = (chosenImages + chosenImages).shuffled()
        cards = randomizedImages.map{MemoryCard(it) }
    }

    fun flipCard(position: Int) : Boolean{
        val card = cards[position]

        //Three cases
        // 0 flipped = flip over that card
        // 1 flipped = flip over card and check for match
        // 2 flipped = reset two (if not a match), and flip the 1
        var foundMatch = false
        if (indexOfSingleSelectedCard == null) {
            restoreCards()
            indexOfSingleSelectedCard = position
        } else{
            foundMatch = checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }


        card.isFaceUp = !card.isFaceUp

        return foundMatch
    }

    private fun checkForMatch(position1: Int, position2: Int): Boolean {
        if(cards[position1].identifier != cards[position2].identifier){
            return false
        }
        cards[position1].isMatched = true
        cards[position2].isMatched = true
        numPairsFound++
        return true
    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    fun haveWonGame(): Boolean {
        return numPairsFound == boardSize.getNumPairs()
    }

    fun isCardFaceUp(position: Int): Boolean {
        return cards[position].isFaceUp
    }
}