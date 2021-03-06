package fit;

import fit.FitServer;
import fit.Fixture;
import fit.Parse;

import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.javasupport.JavaEmbedUtils;
import org.jruby.runtime.builtin.IRubyObject;

import java.util.ArrayList;

class JRubyFitServer extends FitServer
{

    public static void main(String[] argv) throws Exception
    {
	JRubyFitServer fitServer = new JRubyFitServer();
	fitServer.run(argv);
	System.exit(fitServer.exitCode());
    }

    public JRubyFitServer() 
    {
	super();
	this.fixture = newJRubyFixture();
    }
    
    protected Fixture newFixture() 
    {
	Fixture javaJrubyFixture = newJRubyFixture();
	javaJrubyFixture.listener = fixtureListener;
	return fixture;
    }

    public Fixture newJRubyFixture() 
    {
	RubyInstanceConfig config = new RubyInstanceConfig();
	Ruby runtime = JavaEmbedUtils.initialize(new ArrayList(), config);
	runtime.evalScriptlet("require 'fit/fit'");
	Object jrubyFixture = runtime.evalScriptlet("Fit::Fixture.new");
	Fixture javaJrubyFixture = (Fixture) JavaEmbedUtils.rubyToJava(runtime, (IRubyObject) jrubyFixture, Fixture.class);
	return javaJrubyFixture;
    }
    
    
}