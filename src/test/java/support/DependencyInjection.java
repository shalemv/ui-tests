package support;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import cucumber.api.guice.CucumberModules;
import cucumber.runtime.java.guice.InjectorSource;
import cucumber.runtime.java.guice.ScenarioScoped;
import org.openqa.selenium.WebDriver;
import support.data.ScenarioData;
import ui.DriverFactory;
import uk.sponte.automation.seleniumpom.dependencies.DependencyInjector;
import uk.sponte.automation.seleniumpom.dependencies.InjectionError;
import uk.sponte.automation.seleniumpom.guice.SeleniumPomGuiceModule;

public class DependencyInjection extends AbstractModule implements InjectorSource, DependencyInjector {
    private Injector injector;

    @Override
    protected void configure(){
        bind(WebDriver.class).toProvider(DriverFactory.class).in(ScenarioScoped.class);
        bind(ScenarioData.class).in(ScenarioScoped.class);

    }

    @Override
    public Injector getInjector(){
        if(injector != null) return injector;
        injector = new SeleniumPomGuiceModule(CucumberModules.SCENARIO, this).getInjector();
        return injector;
    }

    @Override
    public <T> T get(Class<T> aClass) throws InjectionError {
        return getInjector().getInstance(aClass);
    }

}
