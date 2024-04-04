package sirius.core.component.hub.decorator;

/**
 * @author shadow
 * @create 2024-04-04 17:02
 **/
public class MilkDecorator extends CoffeeDecorator {

    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public void makeCoffee() {
        super.makeCoffee();
        addMilk();
    }

    public void addMilk() {
        System.out.println("添加牛奶");
    }
}
