package edu.gatech.seclass.myapplication

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import edu.gatech.seclass.myapplication.models.BoardSize
import edu.gatech.seclass.myapplication.models.MemoryCard
import kotlin.math.min


class MemoryBoardAdapter(
    private val context: Context,
    private val boardSize: BoardSize,
    private val cards: List<MemoryCard>,
    private val cardClickListener: CardClickListener
) :
    RecyclerView.Adapter<MemoryBoardAdapter.ViewHolder>() {

    companion object{
        private const val MARGIN_SIZE = 10
        private const val TAG = "MemoryBoardAdapter"
    }

    interface CardClickListener {
        fun onCardClicked (position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardWith = parent.width/ boardSize.getWidth() - (MARGIN_SIZE * 2)
        val cardHeight = parent.height/ boardSize.getHeight() - (MARGIN_SIZE * 2)
        val cardSideLength = min(cardWith,cardHeight)
        val view = LayoutInflater.from(context).inflate(R.layout.memory_card, parent, false)

        val layoutParams = view.findViewById<CardView>(R.id.cardView).layoutParams as MarginLayoutParams
        layoutParams.width = cardSideLength
        layoutParams.height = cardSideLength
        layoutParams.setMargins(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE)


        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = boardSize.numCards


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val imageButton = itemView.findViewById<ImageButton>(R.id.imageButton)



        fun bind(position: Int){
            val memoryCard = cards[position]
            imageButton.setImageResource(if (memoryCard.isFaceUp)
                memoryCard.identifier
            else
                R.drawable.ic_launcher_background)

            imageButton.alpha = if(memoryCard.isMatched) 0.4f else 1.0f

            //If Match change button to grey
            val colorStateList = if(memoryCard.isMatched) ContextCompat.getColorStateList(context, R.color.color_grey) else null

            ViewCompat.setBackgroundTintList(imageButton,colorStateList)

            imageButton.setOnClickListener(){
                Log.i(TAG, "Clicked on position $position")
                cardClickListener.onCardClicked(position)
            }
        }
    }



}

