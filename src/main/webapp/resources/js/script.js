$(function() {
	detectBtnClick();
	keyBoardSubmit();
});




// overrides button click
var detectBtnClick = function() {
	$(".num").click(function(e) {
		var n = e.currentTarget.value;
		addNumber(n);
		return false;
	});
	
	$(".dotBtn").click(function(e) {
		addDot();
		return false;
	});
	
	$(".binOpBtn").click(function(e) {
		var n = e.currentTarget.value;
		addBinOperator(n);
		return false;
	});
	
	$(".unOpBtn").click(function(e) {
		var n = e.currentTarget.value;
		addUnOperator(n);
		return false;
	});
	
	$(".clearBtn").click(function(e) {
		var n = e.currentTarget.value;
		clear(n);
		return false;
	});
	
	$(".symBtn").click(function(e) {
		addSymOperator();
		return false;
	})
	
}


// adds a digit if possible
var addNumber = function(n) {
	var screen = $("#simpleKeyBoard\\:expression");
	var phase = $("#simpleKeyBoard\\:phase");
	
	if (phase.val() === "1") {
		screen.val("");
	}
	var text = screen.val();
	
	if (getLastNumber() === "0") {
		screen.val(text.substring(0, text.length-1)+n); 
	}
	else if (isUnuary(getLastChar())) {
		screen.val(text+"*"+n);
	}
	else {
		screen.val(text+n); 
	}
	phase.val("0");
}

// adds a dot if possible
var addDot = function() {
	var screen = $("#simpleKeyBoard\\:expression");
	var phase = $("#simpleKeyBoard\\:phase");
	if (phase.val() === "1") {
		screen.val("");
	}
	
	var last = getLastNumber();
	
	if (last.indexOf(".") === -1) {
		if (last === "")
			screen.val(screen.val()+"0.");
		else 
			screen.val(screen.val()+".");
	}
	phase.val("0");
}

// adds a binary operator if possible
var addBinOperator = function(n) {
	cleanLastNumber();
	var ignore = ["("];
	var inputs = ["\u00D7", "-", "+", "\u00F7"];
	var replace = ["*", "-", "+", "/"];
	var output = replace[inputs.indexOf(n)];
	var screen = $("#simpleKeyBoard\\:expression");
	var text = screen.val();
	var lastLetter = text.charAt(text.length-1);
	$("#simpleKeyBoard\\:phase").val("0");
	
	if (replace.indexOf(lastLetter) != -1) {
		screen.val(text.substring(0, text.length-1)+output);
	}
	else if (ignore.indexOf(lastLetter) === -1) {
		screen.val(text+output);
	}
}

//adds a unuary operator if possible
var addUnOperator = function(n) {
	cleanLastNumber();
	var ignore = ["(", ")"];
	var inputs = ["%"];
	var replace = ["%"];
	var output = replace[inputs.indexOf(n)];
	var screen = $("#simpleKeyBoard\\:expression");
	var text = screen.val();
	var lastLetter = text.charAt(text.length-1);
	$("#simpleKeyBoard\\:phase").val("0");
	
	if (replace.indexOf(lastLetter) != -1) {
		screen.val(text.substring(0, text.length-1)+output);
	}
	else if (ignore.indexOf(lastLetter) === -1) {
		screen.val(text+output);
	}
}


// applies symmetric operation
var addSymOperator = function() {
	cleanLastNumber();
	var lastNumber = getLastNumber();
	if (lastNumber === "0" || lastNumber === "") return;
	
	var screen = $("#simpleKeyBoard\\:expression");
	var text = screen.val();
	
	var x = text.length-lastNumber.length;
	text = text.substring(0, x);
	text += "(-"+lastNumber+")";
	
	screen.val(text);
}


// deletes screen
var clear = function(n) {
	var screen = $("#simpleKeyBoard\\:expression");
	var phase = $("#simpleKeyBoard\\:phase");
	var sc;
	
	if (n === "AC" || phase.val() === "1") {
		screen.val("0");
	}
	else {
		sc = screen.val();
		if (sc.length === 1) screen.val("0");
		else screen.val(sc.substring(0, sc.length-1));
	}
	
	phase.val("0");
}


// returns last number in the expression (if any)
var getLastNumber = function() {
	var screen = $("#simpleKeyBoard\\:expression").val();
	var lastNumber = "";
	
	var index = screen.length-1;
	var char = screen.charAt(index);
	var par = 0;
	
	while ((char >= "0" && char <= "9") || char === "." || char === ')' || par > 0) {
		if (char === ')') par++;
		else if (char === '(') par--;
		lastNumber = char+lastNumber;
		char = screen.charAt(--index);
	}
	
	return lastNumber;
}

// returns last char
var getLastChar = function() {
	var screen = $("#simpleKeyBoard\\:expression").val();
	if (screen.length > 0) return screen.charAt(screen.length-1);
	return '';
}


// removes last dot
var cleanLastNumber = function() {
	var screen = $("#simpleKeyBoard\\:expression");
	var text = screen.val();
	if (text.charAt(text.length-1) === '.') {
		screen.val(text.substring(0, text.length-1));
	}
}


// 
var isUnuary = function(n) {
	var symbols = ["%", "!"];
	return (symbols.indexOf(n) != -1);
}



// pressing enter
var keyBoardSubmit = function() {
	$(document).keydown(function(e) {
		alert(getLastNumber());
		var key = e.which;
		if (key === 13) {
			$("#simpleKeyBoard\\:keyEqualsSimples").click();
			return false;
		}
	});
}
