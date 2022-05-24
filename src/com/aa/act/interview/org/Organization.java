package com.aa.act.interview.org;

import java.util.*;




public abstract class Organization {

	private Position root;
	
	public Organization() {
		root = createOrganization();
	}
	
	protected abstract Position createOrganization();
	
	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person The name of the new employee
	 * @param title  The position the employee is being hired into
	 * @return the newly filled position or empty if no position has that title
	 */
	public Optional<Position> hire(Name person, String title) {
		Optional<Position> position = getPosition(root, title);
		if (position.isPresent()) {
			Employee employee = new Employee(getNewId(), person);
			position.ifPresent(pos -> pos.setEmployee(Optional.of(employee)));
		}
		return position;
	}

	/**
	 * Gets the position with the given title
	 * @param title The name of the title of the position
	 * @return      The position with the title, or empty if there is no position with that title
	 */
	public Optional<Position> getPosition(Position pos, String title) {
		Optional<Position> output = Optional.empty();
		if (pos.getTitle().toLowerCase().equals(title.toLowerCase())) {
			output = Optional.of(pos);
		} else {
			for (Position p : pos.getDirectReports()) {
				if (output.isEmpty()) {
					output = getPosition(p, title);
				}
			}
		}
		return output;
	}

	/**
	 * Gets a new 8-digit identifier number
	 * @return An 8-digit integer
	 */
	public int getNewId() {
		int id;
		int max = 99999999;
		int min = 10000000;
		id = (int) Math.floor(Math.random()*(max-min+1)+min);
		return id;
	}

	@Override
	public String toString() {
		return printOrganization(root, "");
	}
	
	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for(Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}