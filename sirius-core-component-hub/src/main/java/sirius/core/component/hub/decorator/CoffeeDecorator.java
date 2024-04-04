package sirius.core.component.hub.decorator;

/**
 * @author shadow
 * @create 2024-04-04 17:00
 **/
public abstract class CoffeeDecorator implements Coffee {
    /**
     * 委托对象
     */
    private Coffee delegate;

    public CoffeeDecorator(Coffee coffee) {
        this.delegate = coffee;
    }

    @Override
    public void makeCoffee() {
        delegate.makeCoffee();
    }
}
