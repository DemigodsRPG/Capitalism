package com.censoredsoftware.capitalism.util;

import java.text.DecimalFormat;

public class Maths
{
	public static double roundMoney(double amount)
	{
		DecimalFormat decimalFormat = new DecimalFormat("#.##");

		String formattedAmount = decimalFormat.format(amount);

		formattedAmount = formattedAmount.replace(",", ".");

		return Double.valueOf(formattedAmount);
	}
}
