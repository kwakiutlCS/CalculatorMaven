$(function() {
	detectBtnClick();
	keyBoardSubmit();
	disableInput();
	detectPressedKeys();
	changeScienceView();
	
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
	});
	
	$(".functionBtn").click(function(e) {
		var n = e.currentTarget.value;
		addFunction(n);
		return false;
	});
	
	$(".symbolBtn").click(function(e) {
		var n = e.currentTarget.value;
		addSymbol(n);
		return false;
	});
}


// adds a digit if possible
var addNumber = function(n) {
	var screen = $("#simpleKeyBoard\\:expression");
	var phase = $("#simpleKeyBoard\\:phase");
	
	if (phase.val() === "1" || phase.val() === "2") {
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
	if (phase.val() === "1" || phase.val() === "2") {
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
	var phase = $("#simpleKeyBoard\\:phase");
	if (phase.val() === "2") {
		return;
	}
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
	if (isBinary(getLastChar())) return;
	
	var phase = $("#simpleKeyBoard\\:phase");
	if (phase.val() === "1" || phase.val() === "2") {
		return;
	}
	cleanLastNumber();
	var ignore = ["(", ")"];
	var inputs = ["%", "n!"];
	var replace = ["%", "!"];
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
	var phase = $("#simpleKeyBoard\\:phase");
	if (phase.val() === "1" || phase.val() === "2") {
		return;
	}
	var lastNumber = getLastNumber();
	if (lastNumber === "0" || lastNumber === "") return;
	
	var screen = $("#simpleKeyBoard\\:expression");
	var text = screen.val();
	
	var x = text.length-lastNumber.length;
	text = text.substring(0, x);
	text += "(-"+lastNumber+")";
	
	screen.val(text);
}

var addFunction = function(n) {
	cleanLastNumber();
	var phase = $("#simpleKeyBoard\\:phase");
	var screen = $("#simpleKeyBoard\\:expression");
	var text = screen.val();
	
	if (phase.val() === "1" || phase.val() === "2") {
		text = "";
	}
	
	if (getLastNumber() === "0") {
		 text = (text.substring(0, text.length-1)); 
	}
	
	text += n+"(";
	screen.val(text);
	phase.val("0");
}

var addSymbol = function(n) {
	cleanLastNumber();
	var phase = $("#simpleKeyBoard\\:phase");
	var screen = $("#simpleKeyBoard\\:expression");
	var text = screen.val();
	
	if (phase.val() === "1" || phase.val() === "2") {
		text = "";
	}
	
	text += n;
	screen.val(text);
	phase.val("0");
}


// deletes screen
var clear = function(n) {
	var screen = $("#simpleKeyBoard\\:expression");
	var phase = $("#simpleKeyBoard\\:phase");
	var sc;
	
	if (n === "AC" || phase.val() === "1" || phase.val() === "2") {
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

var isBinary = function(n) {
	var symbols = ["+", "/", "*", "-", "^"];
	return (symbols.indexOf(n) != -1);
}



// pressing enter
var keyBoardSubmit = function() {
	$(document).keydown(function(e) {
		var key = e.which;
		if (key === 13) {
			$("#simpleKeyBoard\\:keyEqualsSimples").click();
			$("#simpleKeyBoard\\:keyEqualsCientifico").click();
			return false;
		}
	});
}

var disableInput = function() {
	$("#simpleKeyBoard\\:expression").keypress(function() {
		return false;
	});
	$("#simpleKeyBoard\\:expression").keydown(function() {
		return false;
	});
	$("#simpleKeyBoard\\:expression").keyup(function() {
		return false;
	});
}

//deals with keyboard strokes
var detectPressedKeys = function() {
	$(document).keydown(function(e) {
		var key = e.which;
		var mapping = {
				13: "EqualsSimples",
				106: "Mult",
				107: "Add",
				109: "Minus",
				110: "Dot",
				111: "Div",
		};
		
		if (key >= 48 && key <= 57) {
			$("#simpleKeyBoard\\:key"+(key-48)).click();
		}
		else if (key >= 96 && key <= 105) {
			$("#simpleKeyBoard\\:key"+(key-96)).click();
		}
		else if (key in mapping) {
			$("#simpleKeyBoard\\:key"+mapping[key]).click();
		}
	});
}


var changeScienceView = function() {
	$(".invBtn").click(function() {
		$.each($(".normalScience"), function() {
			var o = $(this);
			var text = o.val();
			if (text.charAt(0) != 'a')
				o.val("a"+text);
			else
				o.val(text.substring(1, text.length));
		});
		return false;
	});	
}

