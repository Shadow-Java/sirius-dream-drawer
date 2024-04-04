package sirius.core.component.hub.decorator;

/**
 * 原味咖啡，被装饰者对象
 * @author shadow
 * @create 2024-04-04 16:59
 **/
public class OriginalCoffee implements Coffee {


    @Override
    public void makeCoffee() {
        System.out.println("制作原味咖啡");
    }
}
