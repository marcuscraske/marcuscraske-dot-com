/*
    Amazon S3 Bucket listing.

    Copyright (C) 2008 Francesco Pasqualini

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

*/

// fetch list when document loaded
document.body.onload = getList;

function getSpace(s,l){
    var ret = "";
    while(s.length+ret.length<l){
      ret = ret + " ";
    }
    return ret;
}


location.querystring = (function() {
    // The return is a collection of key/value pairs
    var result = {};

    // Gets the query string with a preceeding '?'
    var querystring = location.search;

    // document.location.search is empty if a query string is absent
    if (!querystring)
        return result;

    // substring(1) to remove the '?'
    var pairs = querystring.substring(1).split("&");
    var splitPair;

    // Load the key/values of the return collection
    for (var i = 0; i < pairs.length; i++) {
        splitPair = pairs[i].split("=");
        result[splitPair[0]] = splitPair[1];
    }

    return result;
})();

function createRequestObject(){
    var request_o; //declare the variable to hold the object.
    var browser = navigator.appName; //find the browser name
    if(browser == "Microsoft Internet Explorer"){
        /* Create the object using MSIE's method */
        request_o = new ActiveXObject("Microsoft.XMLHTTP");
    }else{
        /* Create the object using other browser's method */
        request_o = new XMLHttpRequest();
    }
    return request_o; //return the object
}

/* You can get more specific with version information by using
    parseInt(navigator.appVersion)
    Which will extract an integer value containing the version
    of the browser being used.
*/
/* The variable http will hold our new XMLHttpRequest object. */
var http = createRequestObject();
function getList(){
    http.open('get', location.protocol+'//'+location.hostname);
    http.onreadystatechange = handleList;
    http.send(null);
}

function handleList(){
    /* Make sure that the transaction has finished. The XMLHttpRequest object
        has a property called readyState with several states:
        0: Uninitialized
        1: Loading
        2: Loaded
        3: Interactive
        4: Finished */
    if(http.readyState == 4){ //Finished loading the response
        /* We have got the response from the server-side script,
            let's see just what it was. using the responseText property of
            the XMLHttpRequest object. */
        var response = http.responseXML;

filex = response.getElementsByTagName('Contents');


res = '';
fileList = new Array();
for(i=0; i<filex.length; i++){
        fileData =new Array();
        fileList[i] = fileData;
    size = filex[i].getElementsByTagName('Size')[0].firstChild.data;
    name = filex[i].getElementsByTagName('Key')[0].firstChild.data;
    lastmod = filex[i].getElementsByTagName('LastModified')[0].firstChild.data;
        link = "<A HREF=\""+name+"\">"+name+"</A>";
        fileData[0] = name;
        fileData[1] = size;
        fileData[2] = lastmod;
        fileData[3] = link;
}
fileList.sort(getSort());
//document.write(getSort());
for(i=0; i<fileList.length; i++){ //length is the same as count($array)
        fileData = fileList[i];
        name = fileData[0];
        size = fileData[1];
        lastmod = fileData[2];
        link = fileData[3];
        res = res +  getSpace(size,15) +size + " B ";
        res = res + " "+ getSpace(lastmod,20)+ lastmod + " ";
        res = res + " "+ link+ getSpace(name,50) + " ";
        res = res + "<BR>";
}


        document.getElementById('bucket_list').innerHTML = "<PRE>"+getLink()+"<BR>"+res+"</PRE>"  ;
    }
}


function getQueryVariable(variable) {
var query = window.location.search.substring(1);
var vars = query.split("&");
for (var i=0;i<vars.length;i++) {
var pair = vars[i].split("=");
if (pair[0] == variable) {
return pair[1];
}
}
return null;
}


function sortSize(a,b) {
   if(parseInt(a[1]) > parseInt(b[1])) return 1;
   if(parseInt(a[1]) < parseInt(b[1])) return -1;
   return 0;
 }
function sortSizeDesc(a,b) { return (-sortSize(a,b)); }
function sortLastmod(a,b) {
   if(a[2] > b[2]) return 1;
   if(a[2] < b[2]) return -1;
   return 0;
}
function sortLastmodDesc(a,b) { return (-sortLastmod(a,b)); }

function sortName(a,b) {
   if(a[0] > b[0]) return 1;
   if(a[0] < b[0]) return -1;
   return 0;
}
function sortNameDesc(a,b) { return -sortName(a,b); }
//document.write('http://'+location.hostname);

function getSort(){
  var s = getQueryVariable("sort");
  var d = getQueryVariable("sortdir");
  if(s=='size'){ return d == 'desc' ? sortSizeDesc : sortSize};
  if(s=='name'){ return d == 'desc' ? sortNameDesc : sortName};
  if(s=='lastmod'){ return d == 'desc' ? sortLastmodDesc : sortLastmod};
  return sortName;
}


function getLink(){
  return "             "+getLinkSize() + "  " + getLinkLastmod() + "              " + getLinkName() + "   " ;
}

function getNextSortDir(sortCol){
  if (sortCol == getQueryVariable("sort"))
      return getQueryVariable("sortdir") == 'desc' ? 'asc' : 'desc';
  return 'asc'
}

function getLinkSize(){
  return "<A HREF=\"?sort=size&sortdir=" +getNextSortDir('size') +"\">Size</A>";
}
function getLinkName(){
  return "<A HREF=\"?sort=name&sortdir=" +getNextSortDir('name') +"\">Name</A>";
}
function getLinkLastmod(){
  return "<A HREF=\"?sort=lastmod&sortdir=" +getNextSortDir('lastmod') +"\">Lastmodified</A>";
}
