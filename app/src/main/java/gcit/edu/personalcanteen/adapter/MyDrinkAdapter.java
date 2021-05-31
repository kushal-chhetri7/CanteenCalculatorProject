package gcit.edu.personalcanteen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import gcit.edu.personalcanteen.R;
import gcit.edu.personalcanteen.eventbus.MyUpdateCartEvent;
import gcit.edu.personalcanteen.listener.ICartLoadListener;
import gcit.edu.personalcanteen.listener.IRecyclerViewClickListener;
import gcit.edu.personalcanteen.model.CartModel;
import gcit.edu.personalcanteen.model.DrinkModel;

public class MyDrinkAdapter extends RecyclerView.Adapter<MyDrinkAdapter.MyDrinkViewHolder> {

    private Context context;
    private List<DrinkModel> drinkModelList;
    private ICartLoadListener iCartLoadListener;

    public MyDrinkAdapter(Context context, List<DrinkModel> drinkModelList, ICartLoadListener iCartLoadListener) {
        this.context = context;
        this.drinkModelList = drinkModelList;
        this.iCartLoadListener = iCartLoadListener;
    }

    @NonNull

    @Override
    public MyDrinkViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new MyDrinkViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_drink_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull  MyDrinkAdapter.MyDrinkViewHolder holder, int position) {
        Glide.with(context)
                .load(drinkModelList.get(position).getImage())
                .into(holder.imageView);
        holder.txtPrice.setText(new StringBuffer("Nu ").append(drinkModelList.get(position).getPrice()));
        holder.txtName.setText(new StringBuilder().append(drinkModelList.get(position).getName()));


       holder.setListener((view, adapterPosition) -> {
        addToCart(drinkModelList.get(position));
       });
    }

    private void addToCart(DrinkModel drinkModel) {
        DatabaseReference userCart= FirebaseDatabase
                .getInstance()
                .getReference("Cart")
                .child("UNIQUE_USER_ID");
        userCart.child(drinkModel.getKey())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        if(snapshot.exists())
                        { //If user already have the item in the cart
                                //just update quantity and totalPrice
                            CartModel cartModel= snapshot.getValue(CartModel.class);
                            cartModel.setQuantity(cartModel.getQuantity()+1);
                            Map<String,Object> updateData=new HashMap<>();
                            updateData.put("Quantity",cartModel.getQuantity());
                            updateData.put("TotalPrice",cartModel.getQuantity()*Float.parseFloat(cartModel.getPrice()));

                            userCart.child(drinkModel.getKey())
                                    .updateChildren(updateData)
                                    .addOnSuccessListener(avoid -> {
                                        iCartLoadListener.onCartLoadFailed("Add To Cart Success");
                                    })
                                    .addOnFailureListener(e -> iCartLoadListener.onCartLoadFailed(e.getMessage()));
                        }
                        else
                            {
                                //If item not have in cart, add new
                                CartModel cartModel=new CartModel();
                                cartModel.setName(drinkModel.getName());
                                cartModel.setImage(drinkModel.getImage());
                                cartModel.setKey(drinkModel.getKey());
                                cartModel.setPrice(drinkModel.getPrice());
                                cartModel.setQuantity(1);
                                cartModel.setTotalPrice(Float.parseFloat(drinkModel.getPrice()));

                                userCart.child(drinkModel.getKey())
                                        .setValue(cartModel)
                                        .addOnSuccessListener(avoid -> {
                                iCartLoadListener.onCartLoadFailed("Add To Cart Success");
                            })
                                    .addOnFailureListener(e -> iCartLoadListener.onCartLoadFailed(e.getMessage()));



                            }
                        EventBus.getDefault().postSticky(new MyUpdateCartEvent());
                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {
        iCartLoadListener.onCartLoadFailed(error.getMessage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return drinkModelList.size();
    }

    public class MyDrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtPrice)
        TextView txtPrice;

       IRecyclerViewClickListener listener;

        public void setListener(IRecyclerViewClickListener listener) {
            this.listener = listener;

        }

        private Unbinder unbinder;
        public MyDrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder= ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        listener.onRecycleClick(v,getAdapterPosition());
        }
    }
}
