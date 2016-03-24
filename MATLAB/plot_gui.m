function varargout = plot_gui(varargin)
% PLOT_GUI MATLAB code for plot_gui.fig
%      PLOT_GUI, by itself, creates a new PLOT_GUI or raises the existing
%      singleton*.
%
%      H = PLOT_GUI returns the handle to a new PLOT_GUI or the handle to
%      the existing singleton*.
%
%      PLOT_GUI('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in PLOT_GUI.M with the given input arguments.
%
%      PLOT_GUI('Property','Value',...) creates a new PLOT_GUI or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before plot_gui_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to plot_gui_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help plot_gui

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @plot_gui_OpeningFcn, ...
                   'gui_OutputFcn',  @plot_gui_OutputFcn, ...
                   'gui_LayoutFcn',  [] , ...
                   'gui_Callback',   []);
if nargin && ischar(varargin{1})
    gui_State.gui_Callback = str2func(varargin{1});
end

if nargout
    [varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
else
    gui_mainfcn(gui_State, varargin{:});
end
% End initialization code - DO NOT EDIT

global f_name;

% --- Executes just before plot_gui is made visible.
function plot_gui_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to plot_gui (see VARARGIN)

% Choose default command line output for plot_gui
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);

% UIWAIT makes plot_gui wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = plot_gui_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;


% --- Executes on button press in btn_forward.
function btn_forward_Callback(hObject, eventdata, handles)
% hObject    handle to btn_forward (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


count = str2num(get(handles.samples_count,'String'));
offset = str2num(get(handles.samples_offset,'String'));

% if (count < 0 || offset < 0 )
%     set(handles.txt_info,'String','Count & Start > 0 ');
% else
%     set(handles.txt_info,'String','');
% end

diff= count-offset;

set(handles.samples_offset,'String',num2str(count));
set(handles.samples_count,'String',num2str(count+diff));
plot_update(hObject, eventdata, handles);



% --- Executes on button press in btn_backward.
function btn_backward_Callback(hObject, eventdata, handles)
% hObject    handle to btn_backward (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

count = str2num(get(handles.samples_count,'String'));
offset = str2num(get(handles.samples_offset,'String'));
disp(offset)
% if (count < 0 || offset < 0 )
%     set(handles.txt_info,'String','Count & Start > 0 ');
% else
%     set(handles.txt_info,'String','');
% end

diff= count-offset;

set(handles.samples_offset,'String',num2str(offset-diff));
set(handles.samples_count,'String',num2str(count-diff));
plot_update(hObject, eventdata, handles);


function data_filename_Callback(hObject, eventdata, handles)
% hObject    handle to data_filename (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of data_filename as text
%        str2double(get(hObject,'String')) returns contents of data_filename as a double


% --- Executes during object creation, after setting all properties.
function data_filename_CreateFcn(hObject, eventdata, handles)
% hObject    handle to data_filename (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in btn_load.
function btn_load_Callback(hObject, eventdata, handles)
% hObject    handle to btn_load (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

plot_update(hObject, eventdata, handles)


function plot_update(hObject, eventdata, handles)

global f_name
f_name = get(handles.data_filename,'String');
sample_rate = str2num(get(handles.sample_rate,'String'));
count = str2num(get(handles.samples_count,'String'));
offset = str2num(get(handles.samples_offset,'String'));
diff= count-offset;

if (count < 0 || offset < 0 )
    set(handles.txt_info,'String','Count & Start > 0 ');
elseif diff  > 0
    iq_data=read_complex_binary(f_name, diff, offset);
    ax1=plot(handles.plot_time,iq_data,'.');
    grid(handles.plot_time,'on')
    t=offset/sample_rate:1/sample_rate:(count-1)/sample_rate;
    ax2=plot(handles.plot_time2,t,real(iq_data),t,imag(iq_data));
    grid(handles.plot_time2,'on')

    set(handles.txt_info,'String','');
   
else
    set(handles.txt_info,'String','End should > Start');
end



function sample_rate_Callback(hObject, eventdata, handles)
% hObject    handle to sample_rate (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of sample_rate as text
%        str2double(get(hObject,'String')) returns contents of sample_rate as a double


% --- Executes during object creation, after setting all properties.
function sample_rate_CreateFcn(hObject, eventdata, handles)
% hObject    handle to sample_rate (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end



function samples_count_Callback(hObject, eventdata, handles)
% hObject    handle to samples_count (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of samples_count as text
%        str2double(get(hObject,'String')) returns contents of samples_count as a double


% --- Executes during object creation, after setting all properties.
function samples_count_CreateFcn(hObject, eventdata, handles)
% hObject    handle to samples_count (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end



function samples_offset_Callback(hObject, eventdata, handles)
% hObject    handle to samples_offset (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of samples_offset as text
%        str2double(get(hObject,'String')) returns contents of samples_offset as a double


% --- Executes during object creation, after setting all properties.
function samples_offset_CreateFcn(hObject, eventdata, handles)
% hObject    handle to samples_offset (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes during object creation, after setting all properties.
function plot_time_CreateFcn(hObject, eventdata, handles)
% hObject    handle to plot_time (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: place code in OpeningFcn to populate plot_time


% --- Executes during object deletion, before destroying properties.


% --- Executes during object creation, after setting all properties.
function txt_info_CreateFcn(hObject, eventdata, handles)
% hObject    handle to txt_info (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called


% --- Executes during object creation, after setting all properties.
function text8_CreateFcn(hObject, eventdata, handles)
% hObject    handle to text8 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called
