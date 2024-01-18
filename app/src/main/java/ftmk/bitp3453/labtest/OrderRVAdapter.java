package ftmk.bitp3453.labtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderRVAdapter extends RecyclerView.Adapter<OrderRVAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<Pizza> orderModalArrayList;
    private Context context;

    // constructor
    public OrderRVAdapter(ArrayList<Pizza> orderModalArrayList, Context context) {
        this.orderModalArrayList = orderModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderpizza_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        Pizza modal = orderModalArrayList.get(position);
        holder.orderIDTV.setText(modal.getOrderID());
        holder.pizzaIDTV.setText(modal.getPizzaID());
        holder.quantity.setText(modal.getQuantity());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return orderModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView orderIDTV, pizzaIDTV, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            orderIDTV = itemView.findViewById(R.id.idTVCourseName);
            pizzaIDTV = itemView.findViewById(R.id.idTVCourseTracks);
            quantity = itemView.findViewById(R.id.idTVCourseDuration);
        }
    }
}

