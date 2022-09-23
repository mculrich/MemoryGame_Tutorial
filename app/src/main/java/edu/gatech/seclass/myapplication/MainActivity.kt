package edu.gatech.seclass.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import edu.gatech.seclass.myapplication.models.BoardSize
import edu.gatech.seclass.myapplication.models.MemoryCard
import edu.gatech.seclass.myapplication.models.MemoryGame
import edu.gatech.seclass.myapplication.utils.DEFAULT_ICONS

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "Main Activity"
    }

    private lateinit var clRoot: ConstraintLayout
    private lateinit var memoryGame: MemoryGame
    private lateinit var rvBoard: RecyclerView
    private lateinit var tvNumMoves: TextView
    private lateinit var tvNumPairs: TextView

    private  lateinit var adapter:MemoryBoardAdapter

    private var boardSize = BoardSize.EASY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clRoot = findViewById(R.id.clRoot)
        rvBoard = findViewById(R.id.rvBoard)
        tvNumMoves = findViewById(R.id.tvNumMoves)
        tvNumPairs = findViewById(R.id.tvNumPairs)

        memoryGame = MemoryGame(boardSize)

        adapter = MemoryBoardAdapter(this, boardSize, memoryGame.cards, object:MemoryBoardAdapter.CardClickListener {
            override fun onCardClicked(position: Int) {

                updateGameWithFlip(position)
            }

        })

        rvBoard.adapter = adapter

        rvBoard.setHasFixedSize(true)
        rvBoard.layoutManager = GridLayoutManager( this, boardSize.getWidth())
    }

    private fun updateGameWithFlip(position: Int) {
        //Error handling
        if(memoryGame.haveWonGame()){
            //alert the user of won
            Snackbar.make(clRoot,"You already won!",Snackbar.LENGTH_LONG).show()
            return
        }
        if (memoryGame.isCardFaceUp(position)){
            //alert user of invalid move
            Snackbar.make(clRoot,"Invalid Move",Snackbar.LENGTH_LONG).show()
            return
        }

        //actually flip
        if (memoryGame.flipCard(position)){
            Log.i(TAG, "Found a match! Num Pairs found: ${memoryGame.numPairsFound}")
        }
        adapter.notifyDataSetChanged()


    }
}