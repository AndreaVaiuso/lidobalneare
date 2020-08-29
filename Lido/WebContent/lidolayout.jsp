<%@ page import="java.util.ArrayList"%>
<%@ page import="it.lidobalneare.bean.Chair"%>
<%@ page import="it.lidobalneare.bean.User"%>
<%@ page import="it.lidobalneare.db.DBConnect"%>
<div class="contentdivscreen-layout">
	<div style="overflow-x: auto">
		<div id="beachlayoutdiv" class="beachlayoutdiv"
			ondragover="onDragOver(event);" ondrop="onDrop(event);">
			<%
			User cntusr = (User) session.getAttribute("connecteduser");
			ArrayList<Chair> chairSchema = DBConnect.getChairLayout();
			for(int i = 0; i<chairSchema.size();i++){
				%><div class="chair"  id="chair_<%=i%>" <% if(cntusr.getRole().equals("admin")){ %>draggable="true" ondragstart="onDragStart(event,'chair_<%=i%>','<%= chairSchema.get(i).getChairname() %>')" <% } %>
				onmouseover="showChairPopup('chair_<%=i%>')" onmouseout="hideChairPopup()" style="top: <%= chairSchema.get(i).getY() %>px; left: <%= chairSchema.get(i).getX() %>px;">
						<div class="chairpopup" style="display: none">
							<span class="popupchairname" id="chair_<%=i%>_name"><%= chairSchema.get(i).getChairname() %></span>
							<% 
							if(cntusr.getRole().equals("admin")){
								%>
								<button class="btn btn-primary btn-sm popupbutton" type="button" onclick="updateChairToLayout('<%= chairSchema.get(i).getChairname() %>')">Update</button>
								<button class="btn btn-primary btn-sm popupbutton" type="button" onclick="removeChairFromLayout('<%= chairSchema.get(i).getChairname() %>')">Delete</button>
								<%
							} else if(cntusr.getRole().equals("customer") || cntusr.getRole().equals("ticket")){
								//TODO Select chair to book it.
							} else if(cntusr.getRole().equals("lifeguard")){
								//TODO Watch prenotations
							}
						%>
						</div> 
					</div>
				<%
			}
			%>
		</div>
	</div>
</div>