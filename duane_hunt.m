clc, clear, close all;
 
graphname = input('Graph name: \n','s');    % 
file_list = dir();                          % Ask user for name of the graph
file_list(4).name;                          % Reads all of the files in the current directory 
len = size(file_list);                      % INCLUDE ALL FILES FOR ANALYSIS IN THE ACTIVE DIRECTORY
                                            %files should be .txt files with delimiters

file_list.name;                             % Test case for errors

C = {[0 0.4470 0.7410]; [0.8500 0.3250 0.0980]; [0.9290 0.6940 0.1250]; [0.4940 0.1840 0.5560]; [0.4660 0.6740 0.1880]; [0.3010 0.7450 0.9330]; [0.6350 0.0780 0.1840]; [0 0 0]; [0 0.4470 0.7410]; [0.8500 0.3250 0.0980]; [0 0 0]; [0 0 0]; [0 0 0]; [0 0 0]; [0 0 0]; [0 0 0]};	
%creates list for custom colors on the graph
fig = figure;


 for k = 1:len(1)
    hold on;
    name = file_list(k).name;                               %
    [filePath, fileName, fileExtension] = fileparts(name);  % Reads files in order they are named 
    lower(fileExtension);                                   % Name files with numbers at the beginning, ex: 1G19_1uM, 2G19_5uM 
                                                            %
                                                           
                                                          
    switch lower(fileExtension)             %
        case '.txt'                         % Checks if the file is a text file and extracts all data into the lists 'temp' and 'abs'
            fileID = fopen(name,'r');       %
            formatSpec = '%f\t%f';          %format of csv (middle is delimitter)
            sizeData = [2 Inf];             
  
            data = fscanf(fileID,formatSpec,sizeData);      %reads data into array
            data = data';
    
            fclose(fileID); 
            fprintf('The file name is %s:\n',name)                            %outputs current filename
            sample = input('Enter the name/number of the sample: \n','s');  %This is also the name used in the legend
            
            for i = 1:length(data)
                lambda(i) = data(i);                                    %puts data into 1-D arrays
                intensity(i) = data(i,2);                               %
            end
                                           % 
            lambda = lambda(1:length(lambda)-3);          % Adjust data as necessary to remove noise 
            intensity = intensity(1:length(intensity)-3) ;    % here we remove final 3 data points

                                                       % 
            %intensity = intensity + - intensity(1);   % fix the relative abs and normalize if desired
            %abs = abs/max(abs);                       %
   

            %Find point of maximum slope in data
            %we will search over many increments to find a range of slopes
            %that fit our data. the highest and lowest of these will be
            %considered the range
 
            incrementstart = 2;                 %where to start the search
            increment = incrementstart;         %variable for iterating
            incrementmax = 10;                  %where to end the search
            for i = 1:(incrementmax-incrementstart)
                highslope = 0;                            
                max = round(length(lambda)-increment);          
                count = 1;
                for j = 1:max                             %here we find the x and y for each group of numbers, this is controlled by the increment (if increment = 3, consider 3 points)
                    x = lambda(count:count+increment);               % this is where the real adjustment can take place
                    y = intensity(count:count+increment);             % increase the number of data points that are analyzed 
                 fit = polyfit(x,y,1);           %  fit the points we consider above              
              
                  if fit(1) > highslope       %
                      highslope = fit(1);     % Checks if the Slope is greater then the previous highest slope 
                      Tt = fit(2);            % store slope and intercept for the highest slope
                  end
                  count = count + 1;        %move on to next set of points
                end
                slope(i) = highslope;       %insert highest slope/intercept into array for given increment
                intercept(i) = Tt;          %
                incrementused(i) = increment;
                increment = increment + 1;  %increase increment 
            end
            
            maxslope = 0;                   %initialize max and min slope
            minslope = 1000;                %set min to be much higher than expected highest slope
            for i = 1:(incrementmax-incrementstart) %determine if slope is highest or lowest out of all increments
                
                if slope(i) > maxslope              %store highest slope
                    maxslope = slope(i);
                    maxinter = intercept(i);
                    maxincrement = incrementused(i);
                end
                
                if slope(i) < minslope              %store lowest slope
                    minslope = slope(i);
                    mininter = intercept(i);
                    minincrement = incrementused(i);
                end
                
            end
            
            plotmax= maxslope*lambda+maxinter;          % plot the highest slope line
            lambdamax = -maxinter/maxslope;             % find lambda min based on highest slope (the x-intercept)
            plotmin = minslope*lambda+mininter;         %plot the lowest slope line
            lambdamin = -mininter/minslope;             %find lambda min based on lowest slope
        
            hold on;
            hold all; %
            points = plot(lambda,intensity, 'x', 'DisplayName',sprintf('%s',sample))    %plots x and y, stores display name for legend      
            %maxlam = plot(0,0,'o', 'DisplayName',sprintf('Max Slope \\lambda_{min} = %f pm', lambdamax))      %
            maxplot = plot(lambda,plotmax, 'DisplayName', sprintf('Max Slope; Increment = %d \n\\lambda_{min} = %0.3f pm', maxincrement, lambdamax))
            %minlam = plot(0,0,'o')
            minplot = plot(lambda,plotmin,'--', 'DisplayName',sprintf('Min Slope; Increment = %d \n\\lambda_{min} = %0.3f pm', minincrement, lambdamin))
            
            

            

            fprintf('The min wavelength for %s is between %0.4f %0.4f \n\n',name,lambdamax,lambdamin) %outputs x-intercept range for lambda min
            
            
            title(sprintf('%s',graphname));                 %
            xlabel('Wavelength [pm]');               % Name the graph and label the axis
            ylabel('Count Rate [s^{-1}]');                        %
            axis([20 70 -1 240]);                   %control axes [xmin xmax ymin ymax]
            hold on;        
            
            


            

    end

    
    points.MarkerEdgeColor = C{k};      %recolor points on graph
    %maxlam.MarkerEdgeColor = C{k};
    maxplot.Color = C{k};               %recolor lines
    %minlam.MarkerEdgeColor = C{k};
    minplot.Color = C{k};               %recolor lines
    
    
    drawnow                             %redraw figure
    
    

    
     
 end
leg = legend;                       %draw legend
leg.FontSize = 6;                   %adjust legend and figure size here
leg.Location = 'bestoutside';
leg.Units = 'inches';
fig.Units = 'inches';
fig.Renderer = 'painters';
fig.Position = [0 0 10 6];

legend show                                 %show legend on figure
set(fig, 'PaperPositionMode', 'Auto');      %set paper sizing to auto
saveas(fig, 'Duane Hunt Graph', 'png');     %save figure as PNG in active directory

hold off;                                   %release figure
 
    
