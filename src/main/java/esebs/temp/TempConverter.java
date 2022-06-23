package esebs.temp;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Path("/tempconverter")
public class TempConverter {

    @ServerExceptionMapper
    public RestResponse<String> mapException(UnknownUnitException x) {
        return RestResponse.status(Response.Status.NOT_FOUND, "Unknown Unit: " + x.name);
    }
    @Inject
    ConverterService tempService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return ("Hi," 
        + " use /fahrenheit/{temp}/{unit} or /celsius/{temp}/{unit} to get desired temperature. \n" 
        + "Another option is using the query parameters, using /celsius?temperature=X&unit=Y "
        + " or /fahrenheit?temperature=X&unit=Y where X is the input temerature and Y the input"
        + " unit.");
    }

    
    @GET
    @Path("/celsius/{temp}/{unit}")
    @Produces(MediaType.TEXT_PLAIN)
    public Integer convertToCelsiusPath(int temp, String unit) {
        int temperature = tempService.toCelsius(temp, unit);
        if (temperature == -99999999) {
            throw new UnknownUnitException(unit);
        }
        return temperature;
    }
    
    @GET
    @Path("/fahrenheit/{temp}/{unit}")
    @Produces(MediaType.TEXT_PLAIN)
    public Integer convertToFahrenheitPath(int temp, String unit) {
        int temperature = tempService.toFarenheit(temp, unit);
        if (temperature == -99999999) {
            throw new UnknownUnitException(unit);
        }
        return temperature;
    }

    @GET
    @Path("/celsius")
    @Produces(MediaType.TEXT_PLAIN)
    public Response convertToCelsiusQuery(@QueryParam("temperature") int temp, @QueryParam("unit") String unit) {
        int temperature = tempService.toCelsius(temp, unit);
        if (temperature == -99999999) {
            throw new UnknownUnitException(unit);
        }
        return Response.ok(temperature).build();
    }

    
    @GET
    @Path("/fahrenheit")
    @Produces(MediaType.TEXT_PLAIN)
    public Response convertToFahreneitQuery(@QueryParam("temperature") int temp, @QueryParam("unit") String unit) {
        int temperature = tempService.toFarenheit(temp, unit);
        if (temperature == -99999999) {
            throw new UnknownUnitException(unit);
        }
        return Response.ok(temperature).build();
    }

    @GET
    @Path("/celsius/json")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response convertToCelsiusJResponse(InitialTemp temp) {
        int temperature = tempService.toCelsius(temp.temperature, temp.unit);
        if (temperature == -99999999) { 
            throw new UnknownUnitException(temp.unit);
        }
        return Response.ok(temperature).build();
    }

    @GET
    @Path("/fahrenheit/json")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response convertToFahrenheitJResponse(InitialTemp temp) {
        int temperature = tempService.toFarenheit(temp.temperature, temp.unit);
        if (temperature == -99999999) {
            throw new UnknownUnitException(temp.unit);
        }
        return Response.ok(temperature).build();
    }



    private class UnknownUnitException extends RuntimeException {
        public final String name;
    
        public UnknownUnitException(String name) {
            this.name = name;
        }
    }
}

