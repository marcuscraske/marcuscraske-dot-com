function toolsCopy(element)
{
    var client = new ZeroClipboard();
    ZeroClipboard.setData("text/plain", element.innerText);
}
