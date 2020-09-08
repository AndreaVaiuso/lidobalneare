<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="it.lidobalneare.bean.User"%>
<%@ page import="it.lidobalneare.bean.Chair"%>
<%@ page import="it.lidobalneare.db.DBConnect"%>
<%@ page import="it.lidobalneare.Utility"%>

<div class="contentdivscreen-layout">
	<div style="overflow-x: auto">
		<div id="beachlayoutdiv" class="beachlayoutdiv"	ondragover="onDragOver(event);" ondrop="onDrop(event);">
			<%
				User cntusr = (User) session.getAttribute("connecteduser");
				if(cntusr == null){
					response.sendRedirect("login.html");
					return;
				}
				ArrayList<Chair> chairSchema = DBConnect.getChairLayout();
				for(int i = 0; i<chairSchema.size();i++){
					
					if(cntusr.isAdmin()){
			%>
			<div class="chair" id="chair_<%=i%>" draggable="true" 
			  ondragstart="onDragStart(event,'chair_<%=i%>','<%=chairSchema.get(i).getChairname()%>')"
			  onmouseover="showChairPopup('chair_<%=i%>')"
			  onmouseout="hideChairPopup()" style="top: <%=chairSchema.get(i).getY()%>px; left: <%=chairSchema.get(i).getX()%>px;">
				<div class="chairpopup" style="display: none">
					<span class="popupchairname" id="chair_<%=i%>_name"><%=chairSchema.get(i).getChairname()%></span>
					<hr>
					<button class="btn btn-primary btn-sm popupbutton" type="button" onclick="updateChairToLayout('<%=chairSchema.get(i).getChairname()%>')">
					<i class="fa fa-edit"></i>
					</button>
					<button class="btn btn-primary btn-sm popupbutton" type="button" onclick="removeChairFromLayout('<%=chairSchema.get(i).getChairname()%>')">
					<i class="fa fa-remove"></i>
					</button>
					<button class="btn btn-primary btn-sm popupbutton" type="button" onclick="duplicateChair('<%=chairSchema.get(i).getChairname()%>')">
					<i class="fa fa-clone"></i>
					</button>
				</div>
			</div>
			<%
				} else if(cntusr.isCustomer() || cntusr.isTicket() || cntusr.isLifeGuard() || cntusr.isInfoMonitor()){
						String date = Utility.getDate(request);
						int timeslot = Utility.getTimeSlot(cntusr, request);
						boolean isPass = Utility.isPass(cntusr, request);
						
						String customerOccupied = DBConnect.getChairOccupied(chairSchema.get(i).getChairname(),date,timeslot);
						if(isPass) {customerOccupied = DBConnect.getChairPassOccupied(chairSchema.get(i).getChairname(),date,timeslot+1);}
						
						double price;
						if(timeslot == 0){
							price = chairSchema.get(i).getDailyPrice();
						} else {
							price = chairSchema.get(i).getPrice(); 
						}

						if(customerOccupied.isEmpty()){
			%>
			<div class="chair" id="chair_<%=i%>" onmouseover="showChairPopup('chair_<%=i%>')" onmouseout="hideChairPopup()"
			  style="top: <%= chairSchema.get(i).getY() %>px; left: <%= chairSchema.get(i).getX() %>px;">
				<div class="chairpopup" style="display: none">
					<span class="popupchairname" id="chair_<%=i%>_name"><%= chairSchema.get(i).getChairname() %></span>
					<%
						if(!isPass){
							if(cntusr.isInfoMonitor() || cntusr.isLifeGuard()){
								%>
								<span class="popupchairname" id="chair_<%=i%>_name"> FREE </span>
								<%
							} else {
								%>
								<span class="popupchairname" id="chair_<%=i%>_price">Price: <%=price%> &euro; </span>
								<button class="btn btn-primary btn-sm popupbutton" type="button"
									onclick="bookchair('<%= chairSchema.get(i).getChairname() %>',<%=price%>)">Book</button>
								<%
							}
						} else {
					%>
					<span class="popupchairname" id="chair_<%=i%>_price">Price: <%= chairSchema.get(i).getPassPrice() * (timeslot+1) %>&euro;
					</span>
					<button class="btn btn-primary btn-sm popupbutton" type="button"
						onclick="bookchair('<%= chairSchema.get(i).getChairname() %>',<%= chairSchema.get(i).getPassPrice() * (timeslot+1) %>)">Book</button>
					<%
						}
					%>
				</div>
			</div>
			<%
					} else {
						%>
			<div class="chairoccupied" id="chair_<%=i%>"
				onmouseover="showChairPopup('chair_<%=i%>')"
				onmouseout="hideChairPopup()"
				style="top: <%= chairSchema.get(i).getY() %>px; left: <%= chairSchema.get(i).getX() %>px;">
				<div class="chairpopup" style="display: none; width: auto">
					<span class="popupchairname" id="chair_<%=i%>_name"><%= chairSchema.get(i).getChairname() %></span>
					<%
					if(cntusr.isLifeGuard()){
						%>
						<span class="popupchairname" id="chair_<%=i%>_name"> <%= customerOccupied %> </span>
						<%
					} else if(cntusr.isInfoMonitor()){
						%>
						<span class="popupchairname" id="chair_<%=i%>_name"> Occupied </span>
						<%
					} else {
						%>
						<span class="popupchairname" id="chair_<%=i%>_name"> Chair unavailable </span>
						<%
					}
					%>
				</div>
			</div>
			<%
					}
				} 
			}
			%>
		</div>
	</div>
</div>