package gcit.edu.personalcanteen.listener;


import java.util.List;

import gcit.edu.personalcanteen.model.DrinkModel;

public interface IDrinkLoadListener {
    void onDrinkLoadSuccess(List<DrinkModel> drinkModelList);
    void onDrinkLoadFailed(String message);
}
