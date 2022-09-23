package bo;

import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.PurchaseOrderBOImpl;
import bo.custom.impl.QueryBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){
    }

    public static BOFactory getBoFactory(){
        return boFactory==null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOType{
        CUSTOMER,ITEM,PURCHASE_ORDER,QUERYBO;
    }

    public SuperBO getBO(BOType type){
        switch (type){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case PURCHASE_ORDER:
                return new PurchaseOrderBOImpl();
            case QUERYBO:
                return new QueryBOImpl();
            default:
                return null;
        }
    }
}
