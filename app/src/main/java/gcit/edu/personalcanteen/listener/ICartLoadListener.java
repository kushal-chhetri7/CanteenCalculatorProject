package gcit.edu.personalcanteen.listener;

import java.util.List;

import gcit.edu.personalcanteen.model.CartModel;
import gcit.edu.personalcanteen.model.DrinkModel;

public interface ICartLoadListener {
    void onCartLoadSuccess(List<CartModel> cartModelList);
    void onCartLoadFailed(String message);
}