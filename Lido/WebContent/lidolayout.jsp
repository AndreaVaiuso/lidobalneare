<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="it.lidobalneare.bean.Chair"%>
<%@ page import="it.lidobalneare.bean.User"%>
<%@ page import="it.lidobalneare.bean.Booking"%>
<%@ page import="it.lidobalneare.db.DBConnect"%>
<div class="contentdivscreen-layout">
	<div style="overflow-x: auto">
		<div id="beachlayoutdiv" class="beachlayoutdiv"
			ondragover="onDragOver(event);" ondrop="onDrop(event);">
			<%
			User cntusr = (User) session.getAttribute("connecteduser");
			if(cntusr == null){
				response.sendRedirect("login.html");
				return;
			}
			ArrayList<Chair> chairSchema = DBConnect.getChairLayout();
			for(int i = 0; i<chairSchema.size();i++){
				
				if(cntusr.getRole().equals("admin")){
					%>
					<div class="chair"  id="chair_<%=i%>" draggable="true" ondragstart="onDragStart(event,'chair_<%=i%>','<%= chairSchema.get(i).getChairname() %>')"
						onmouseover="showChairPopup('chair_<%=i%>')" onmouseout="hideChairPopup()" style="top: <%= chairSchema.get(i).getY() %>px; left: <%= chairSchema.get(i).getX() %>px;">
						<div class="chairpopup" style="display: none">
							<span class="popupchairname" id="chair_<%=i%>_name"><%= chairSchema.get(i).getChairname() %></span>
							<hr>
							<button class="btn btn-primary btn-sm popupbutton" type="button" onclick="updateChairToLayout('<%= chairSchema.get(i).getChairname() %>')">Update</button>
							<button class="btn btn-primary btn-sm popupbutton" type="button" onclick="removeChairFromLayout('<%= chairSchema.get(i).getChairname() %>')">Delete</button>
						</div> 
					</div>
					<%
				} else if(cntusr.getRole().equals("customer") || cntusr.getRole().equals("ticket")){
					String date = request.getParameter("date");
					if(date==null){
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date d = new Date();
						date = dateFormat.format(d);
					}
					
					int timeslot = 0;
					try{
						timeslot = Integer.valueOf(request.getParameter("timeslot"));
					} catch (Exception e){}
					Booking b = DBConnect.getChairOccupied(chairSchema.get(i).getChairname(),date,timeslot);
					if(b==null){
						%>
						<div class="chair"  id="chair_<%=i%>" onmouseover="showChairPopup('chair_<%=i%>')" onmouseout="hideChairPopup()" style="top: <%= chairSchema.get(i).getY() %>px; left: <%= chairSchema.get(i).getX() %>px;">
							<div class="chairpopup" style="display: none">
								<span class="popupchairname" id="chair_<%=i%>_name"><%= chairSchema.get(i).getChairname() %></span>
								<span class="popupchairname" id="chair_<%=i%>_price">Price: 
								<% 
								if(timeslot == 0){
									%> <%= chairSchema.get(i).getDailyPrice() %>  <% 
								} else {
									%> <%= chairSchema.get(i).getPrice() %>  <% 
								}
								%> &euro;</span> 
								<button class="btn btn-primary btn-sm popupbutton" type="button" onclick="bookchair('<%= chairSchema.get(i).getChairname() %>')">Book</button>
							</div> 
						</div>
						<%
					} else {
						%>
						<div class="chairoccupied"  id="chair_<%=i%>" onmouseover="showChairPopup('chair_<%=i%>')" onmouseout="hideChairPopup()" style="top: <%= chairSchema.get(i).getY() %>px; left: <%= chairSchema.get(i).getX() %>px;">
							<div class="chairpopup" style="display: none">
								<span class="popupchairname" id="chair_<%=i%>_name">Chair unavailable</span>
							</div> 
						</div>
						<%
					}
					
				} else if(cntusr.getRole().equals("lifeguard")){
					//TODO Watch prenotations
				}

			}
			%>
		</div>
	</div>
</div>