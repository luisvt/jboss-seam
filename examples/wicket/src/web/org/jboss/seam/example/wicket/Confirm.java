
package org.jboss.seam.example.wicket;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.example.wicket.action.Booking;
import org.jboss.seam.example.wicket.action.HotelBooking;
import org.jboss.seam.wicket.SeamLink;

@Restrict
public class Confirm extends WebPage 
{
   
   @In
   private Booking booking;
   
   @In(create=true)
   private HotelBooking hotelBooking;

	public Confirm(final PageParameters parameters)
	{
	   super(parameters);
	   Template body = new Template("body");
	   body.add(new HotelViewPanel("hotel", booking.getHotel()));
	   body.add(new OutputBorder("totalBorder", "Total Payment", new Label("total", DecimalFormat.getCurrencyInstance(Locale.US).format(booking.getTotal()))));
	   body.add(new OutputBorder("checkinDateBorder", "Check in", new Label("checkinDate", new SimpleDateFormat().format(booking.getCheckinDate()))));
	   body.add(new OutputBorder("checkoutDateBorder", "Check out", new Label("checkoutDate", new SimpleDateFormat().format(booking.getCheckoutDate()))));
	   body.add(new OutputBorder("creditCardNumberBorder", "Credit Card #", new Label("creditCardNumber", booking.getCreditCard())));
	   body.add(new SeamLink("revise")
      {
         @Override
         public void onClick()
         {
            hotelBooking.bookHotel();
            setResponsePage(Book.class);
         }
      });
      body.add(new SeamLink("confirm")
      {
         @Override
         public void onClick()
         {
            hotelBooking.confirm();
            setResponsePage(Main.class);
         }
      });
      body.add(new SeamLink("cancel")
      {
         @Override
         public void onClick()
         {
            hotelBooking.cancel();
            setResponsePage(Main.class);
         }
         
      });
	   
	   add(body);
	}
	
	@Override
   protected void onBeforeRender()
   {
	   super.onBeforeRender();
      System.out.println("cid " + Conversation.instance().getId());
   }
	
}
